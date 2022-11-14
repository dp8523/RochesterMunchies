import { Component, OnInit } from '@angular/core';
import { Snack } from '../Snack';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { SnackService } from '../snack.service';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  user: User | undefined;
  items: Snack[] = [];
  quantities: number[] = [];
  costs: number[] = [];
  shoppingCart = new Map<string, number>();
  totalCost = 0;

  constructor(  
    private snackService: SnackService,
    private userService: UserService,
    private location: Location ) {}

  ngOnInit(): void {
    this.getItems();
  }
  
  goBack(): void {
    this.location.back();
  }

  getUser(): void {
    if(sessionStorage['user'] == "" || sessionStorage['user'] == null){
      this.user = undefined;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
    }
  }

  Loggedin(): Boolean{
    
    if(sessionStorage['login'] == "false" || sessionStorage['login'] == null ){
      return false;
    }
    return true;
  }

  isCartEmpty(): Boolean {
    if(sessionStorage['user'] == ""){
      this.user = undefined;
      return true;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
      if (this.user){
        this.shoppingCart = new Map(Object.entries(this.user.cart));
        if(this.shoppingCart.size == 0){
          return true;
        }
      }
      return false;
    }
  }

  getItems(): void {

    if(sessionStorage['user'] == ""){
      this.user = undefined;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
      if (this.user){
        this.shoppingCart = new Map(Object.entries(this.user.cart));

        this.items = [];
        this.quantities = [];
        this.costs = [];

        this.shoppingCart.forEach((value: number, key: string) => {
          const id = Number(key);
        
          this.snackService.getSnack(id).subscribe(snack => {
            this.items.push(snack);
            this.quantities.push(value);

            const num1 = snack.price * value;
            const result = num1.toFixed(2);
            const final = parseFloat(result);
            this.costs.push(final);
          });
          
        })
        this.userService.getTotalCost(this.user.username).subscribe(cost => {
          
          this.totalCost = parseFloat(cost.toFixed(2));
        });        
      }    
    }
  }

  addCart(snackId: number): void{

    if(sessionStorage['user'] == "" || sessionStorage['user'] == null){
      this.user = undefined;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
    }

    if (this.user){
      this.userService.addCart(this.user.username, snackId).subscribe(user => {
        this.user = user;
        sessionStorage.setItem("user", JSON.stringify(this.user));

        this.shoppingCart = new Map(Object.entries(this.user.cart));
        this.items = [];
        this.quantities = [];
        this.costs = [];
     
        this.shoppingCart.forEach((value: number, key: string) => {
          const id = Number(key);
        
          this.snackService.getSnack(id).subscribe(snack => { 
            this.items.push(snack);
            this.quantities.push(value);
            
            const num1 = snack.price * value;
            const result = num1.toFixed(2);
            const final = parseFloat(result);
            this.costs.push(final);
            
          });    
        })
        this.userService.getTotalCost(this.user.username).subscribe(cost => { 
          
          this.totalCost = parseFloat(cost.toFixed(2)); 
        }); 

      });
    }
  }

  deleteCart(snackId: number): void {
    
    if(sessionStorage['user'] == "" || sessionStorage['user'] == null){
      this.user = undefined;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
    }

    if (this.user){

      this.userService.deleteCart(this.user.username, snackId).subscribe(user => {
        
        this.user = user;
        sessionStorage.setItem("user", JSON.stringify(this.user));
        this.shoppingCart = new Map(Object.entries(this.user.cart));
        
        this.items = [];
        this.quantities = [];
        this.costs = [];

        this.shoppingCart.forEach((value: number, key: string) => {
          const id = Number(key);
        
          this.snackService.getSnack(id).subscribe(snack => {
            this.items.push(snack)
            this.quantities.push(value);
            
            const num1 = snack.price * value;
            const result = num1.toFixed(2);
            const final = parseFloat(result);
            this.costs.push(final);

          });
        })
        this.userService.getTotalCost(this.user.username).subscribe(cost => this.totalCost = parseFloat(cost.toFixed(2)));    
      });
    }
  }

  checkoutCart(): void{
    if(sessionStorage['user'] == "" || sessionStorage['user'] == null){
      this.user = undefined;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
    }

    if (this.user){
      this.userService.checkoutCart(this.user.username).subscribe(user => {

        if(user){
          this.user = user;
          sessionStorage.setItem('user', JSON.stringify(this.user));
          this.getItems();
          alert("Successful checkout!")
        }
        else{
          alert("Shopping cart quantity greater than snack quantity");
        }
      })
    }
    

  }

}