import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { Snack } from '../Snack';
import { SnackService } from '../snack.service';

@Component({
  selector: 'app-snack-search',
  templateUrl: './snack-search.component.html',
  styleUrls: ['./snack-search.component.css']
})
export class SnackSearchComponent implements OnInit {

  inventory$!: Observable<Snack[]>
  private searchTerms = new Subject<string>();

  constructor(private snackService: SnackService) { }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.inventory$ = this.searchTerms.pipe(
      debounceTime(300), 
      distinctUntilChanged(),
      switchMap((term: string) => this.snackService.searchSnacks(term)),
    );
  }

}
