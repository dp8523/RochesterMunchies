import { Component, OnInit } from '@angular/core';
import { Snack } from '../Snack';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { SnackService } from '../snack.service';
import { AuthServiceService } from '../auth-service.service';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-buyer-snack-detail',
  templateUrl: './buyer-snack-detail.component.html',
  styleUrls: ['./buyer-snack-detail.component.css']
})
export class BuyerSnackDetailComponent implements OnInit {

  snack: Snack | undefined;
  user: User | undefined;

  constructor(
    private route: ActivatedRoute,
    private snackService: SnackService,
    private location: Location,
    private authService: AuthServiceService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.getSnack();
  }


  getSnack(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.snackService.getSnack(id).subscribe(snack => this.snack = snack);
  }

  goBack(): void {
    this.location.back();
  }

  addCart(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    
    if(sessionStorage['user'] == "" || sessionStorage['user'] == null){
      this.user = undefined;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
    }

    if (this.user){
      this.userService.addCart(this.user.username, id).subscribe(user => {
        this.user = user;
        sessionStorage.setItem("user", JSON.stringify(this.user));
      })
    }
    else {
      alert("Please login to add item to cart");
    }
    
  }


}
