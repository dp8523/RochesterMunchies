import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InventoryComponent } from './inventory/inventory.component';
import { FormsModule } from '@angular/forms';
import { SnackDetailComponent } from './snack-detail/snack-detail.component';

import { HttpClientModule } from '@angular/common/http';
import { SnackSearchComponent } from './snack-search/snack-search.component';
import { BuyerInventoryComponent } from './buyer-inventory/buyer-inventory.component';
import { BuyerSnackDetailComponent } from './buyer-snack-detail/buyer-snack-detail.component';
import { BuyerSearchSnackComponent } from './buyer-search-snack/buyer-search-snack.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    InventoryComponent,
    SnackDetailComponent,
    SnackSearchComponent,
    BuyerInventoryComponent,
    BuyerSnackDetailComponent,
    BuyerSearchSnackComponent,
    ShoppingCartComponent,
    LoginComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
