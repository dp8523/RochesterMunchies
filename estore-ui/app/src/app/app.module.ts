import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InventoryComponent } from './inventory/inventory.component';
import { FormsModule } from '@angular/forms';
import { SnackDetailComponent } from './snack-detail/snack-detail.component';

import { HttpClientModule } from '@angular/common/http';
import { SnackSearchComponent } from './snack-search/snack-search.component';

@NgModule({
  declarations: [
    AppComponent,
    InventoryComponent,
    SnackDetailComponent,
    SnackSearchComponent
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