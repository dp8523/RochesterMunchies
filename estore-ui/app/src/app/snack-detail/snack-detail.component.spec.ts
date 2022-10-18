import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnackDetailComponent } from './snack-detail.component';

describe('SnackDetailComponent', () => {
  let component: SnackDetailComponent;
  let fixture: ComponentFixture<SnackDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SnackDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SnackDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
