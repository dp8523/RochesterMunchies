import { Component, OnInit } from '@angular/core';
import { Snack } from '../Snack';
import { SnackService } from '../snack.service';
import { User } from '../user';
import { LoginComponent } from '../login/login.component';
import { UserService } from '../user.service';


@Component({
  selector: 'app-buyer-inventory',
  templateUrl: './buyer-inventory.component.html',
  styleUrls: ['./buyer-inventory.component.css']
})
export class BuyerInventoryComponent implements OnInit {

  snacks: Snack[] = [];
  snack: Snack = {
      id: 1,
      name: "Oreos",
      description: "Cream cookies",
      quantity: 5,
      price: 3.99   
  };
  
  user: User | undefined;

  constructor(
    private snackService: SnackService,
    private userSerice: UserService,
  ) { }

  ngOnInit(): void {
    this.getSnacks();
  }

  getSnacks(): void {
    this.snackService.getSnacks().subscribe(snacks => this.snacks = snacks);
  }

}
