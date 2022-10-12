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

  constructor(
    private snackService: SnackService
  ) { }

  ngOnInit(): void {
    this.getSnacks();
  }

  getSnacks(): void {
    this.snackService.getSnacks().subscribe(snacks => this.snacks = snacks);
  }

  
}
