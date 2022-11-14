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
  shoppingCart = new Map<string, number>();

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
    if(sessionStorage['user'] == ""){
      this.user = undefined;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
    }
  }

  Loggedin(): Boolean{
    if (sessionStorage['login'] == "true"){
      return true;
    }
    else{
      return false;
    }
  }

  isCartEmpty(): Boolean {
    if(sessionStorage['user'] == ""){
      this.user = undefined;
      return true;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
      if (this.user){
        if(this.user.cart.size == 0){
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
        this.shoppingCart.forEach((value: number, key: string) => {
          const id = Number(key);
        
          this.snackService.getSnack(id).subscribe(snack => {
            this.items.push(snack);
            this.quantities.push(value);
          });
          
        })       
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

        this.shoppingCart.forEach((value: number, key: string) => {
          const id = Number(key);
        
          this.snackService.getSnack(id).subscribe(snack => { 
            this.items.push(snack);
            this.quantities.push(value);
          });    
        })
      });
    }
  }

  deleteCart(snackId: number): void {
    //console.log("Hello");
    
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

        this.shoppingCart.forEach((value: number, key: string) => {
          const id = Number(key);
          
          console.log(key, value);
          this.snackService.getSnack(id).subscribe(snack => {
            this.items.push(snack)
            this.quantities.push(value);
          });
        }) 
       
        
      });
    }
  }


}