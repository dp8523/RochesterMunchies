import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BuyerInventoryComponent } from './buyer-inventory.component';

describe('BuyerInventoryComponent', () => {
  let component: BuyerInventoryComponent;
  let fixture: ComponentFixture<BuyerInventoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuyerInventoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuyerInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
