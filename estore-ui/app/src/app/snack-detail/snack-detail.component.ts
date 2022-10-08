import { Component, OnInit, Input } from '@angular/core';
import { Snack } from '../Snack';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { SnackService } from '../snack.service';

@Component({
  selector: 'app-snack-detail',
  templateUrl: './snack-detail.component.html',
  styleUrls: ['./snack-detail.component.css']
})
export class SnackDetailComponent implements OnInit {

  @Input() snack?: Snack;

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

  save(): void{
    if (this.snack) {
      this.snackService.updateSnack(this.snack).subscribe( () =>this.goBack());
    }
  }

}
