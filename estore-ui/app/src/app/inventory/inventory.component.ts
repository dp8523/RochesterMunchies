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

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.snackService.addSnack({ name } as Snack)
    .subscribe(snack => {
      this.snacks.push(snack);
    })
  }

  delete(snack: Snack): void {
    this.snacks = this.snacks.filter(s => s !== snack);
    this.snackService.deleteSnack(snack.id).subscribe();
  }

  
}
