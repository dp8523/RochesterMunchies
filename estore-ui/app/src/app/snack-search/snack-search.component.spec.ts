import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnackSearchComponent } from './snack-search.component';

describe('SnackSearchComponent', () => {
  let component: SnackSearchComponent;
  let fixture: ComponentFixture<SnackSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SnackSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SnackSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
