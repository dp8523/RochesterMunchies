import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyerSearchSnackComponent } from './buyer-search-snack.component';

describe('BuyerSearchSnackComponent', () => {
  let component: BuyerSearchSnackComponent;
  let fixture: ComponentFixture<BuyerSearchSnackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuyerSearchSnackComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuyerSearchSnackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
