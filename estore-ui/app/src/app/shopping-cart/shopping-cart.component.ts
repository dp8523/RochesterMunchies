import { Component, OnInit } from '@angular/core';
import { Snack } from '../Snack';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { SnackService } from '../snack.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  constructor(  
    private route: ActivatedRoute,
    private snackService: SnackService,
    private location: Location ) {}

  ngOnInit(): void {
  }
  
  goBack(): void {
    this.location.back();
  }

  /*
  addToCart() 
  */

}
