import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventoryComponent } from './inventory/inventory.component';
import { SnackDetailComponent } from './snack-detail/snack-detail.component';

const routes: Routes = [
  {path: 'detail/:id', component: SnackDetailComponent},
  {path: 'inventory', component: InventoryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
