import { Component, OnInit } from '@angular/core';
import { Snack } from '../Snack';
import { SnackService } from '../snack.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {
  
  snacks: Snack[] = [];
  snack: Snack = {
      id: 1,
      name: "Oreos",
      description: "Cream cookies",
      quantity: 5,
      price: 3.99   
  };

  constructor(
    private snackService: SnackService
  ) { }

  ngOnInit(): void {
    this.getSnacks();
  }

  getSnacks(): void {
    this.snackService.getSnacks().subscribe(snacks => this.snacks = snacks);
  }

  add(name: string, description: string, quantity: string, price: string): void {
  
    this.snack.name = name.trim();
    this.snack.description = description.trim();
    this.snack.quantity = +quantity;
    this.snack.price = +price;

    
    /*
    name = name.trim();
    description = description.trim();
    const quantities = +quantity;
    const prices = +price;
    const id = 1; 
    */
    if (!name && !description && !quantity && !price) { return; }
    
    console.log(this.snack);
    this.snackService.addSnack(this.snack)
    .subscribe(snack => {
      this.snacks.push(snack);
    })
  }

  delete(snack: Snack): void {
    this.snacks = this.snacks.filter(s => s !== snack);
    this.snackService.deleteSnack(snack.id).subscribe();
  }

  
}
