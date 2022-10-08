import { Component, OnInit } from '@angular/core';
import { Snack } from '../Snack';
import { SnackService } from '../snack.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  snack: Snack = {
    id: 1,
    name: 'Oreos',
    description: 'Cream cookies',
    price: 5.99
  };
  
  snacks: Snack[] = [];

  selectedSnack?: Snack;

  constructor(
    private snackService: SnackService
  ) { }

  ngOnInit(): void {
    this.getSnacks();
  }

  onSelect(snack: Snack): void {
    this.selectedSnack = snack;
  }

  getSnacks(): void {
    this.snackService.getSnacks().subscribe(snacks => this.snacks = snacks);
  }
}
