import { Component, OnInit } from '@angular/core';
import { Snack } from '../Snack';
import { SnackService } from '../snack.service';
import { User } from '../user';
import { UserService } from '../user.service';
import { AuthServiceService } from '../auth-service.service';


@Component({
  selector: 'app-buyer-inventory',
  templateUrl: './buyer-inventory.component.html',
  styleUrls: ['./buyer-inventory.component.css']
})
export class BuyerInventoryComponent implements OnInit {

  snacks: Snack[] = [];

  constructor(
    private snackService: SnackService,
  ) { }

  ngOnInit(): void {
    this.getSnacks();
  }

  getSnacks(): void {
    this.snackService.getSnacks().subscribe(snacks => this.snacks = snacks);
  }

}
