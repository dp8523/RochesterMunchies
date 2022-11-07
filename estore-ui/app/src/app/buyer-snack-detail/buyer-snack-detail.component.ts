import { Component, OnInit } from '@angular/core';
import { Snack } from '../Snack';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { SnackService } from '../snack.service';

@Component({
  selector: 'app-buyer-snack-detail',
  templateUrl: './buyer-snack-detail.component.html',
  styleUrls: ['./buyer-snack-detail.component.css']
})
export class BuyerSnackDetailComponent implements OnInit {

  snack: Snack | undefined;

  constructor(
    private route: ActivatedRoute,
    private snackService: SnackService,
    private location: Location
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


}
