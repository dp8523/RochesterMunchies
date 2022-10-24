import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BuyerInventoryComponent } from './buyer-inventory/buyer-inventory.component';
import { BuyerSnackDetailComponent } from './buyer-snack-detail/buyer-snack-detail.component';
import { InventoryComponent } from './inventory/inventory.component';
import { LoginComponent } from './login/login.component';
import { SnackDetailComponent } from './snack-detail/snack-detail.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'buyer_detail/:id', component: BuyerSnackDetailComponent},
  {path: 'buyer_inventory', component: BuyerInventoryComponent},
  {path: 'detail/:id', component: SnackDetailComponent},
  {path: 'inventory', component: InventoryComponent},
  {path: 'shopping_cart', component: ShoppingCartComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
