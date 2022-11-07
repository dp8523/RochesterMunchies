import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyerSnackDetailComponent } from './buyer-snack-detail.component';

describe('BuyerSnackDetailComponent', () => {
  let component: BuyerSnackDetailComponent;
  let fixture: ComponentFixture<BuyerSnackDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuyerSnackDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuyerSnackDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
