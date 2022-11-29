import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ToastContainerComponent} from "src/app/components/toast-container/toast-container.component";
import {HomeComponent} from "src/app/components/home/home.component";
import {LayoutComponent} from "src/app/components/layout/layout.component";
import {HeaderComponent} from "src/app/components/layout/header/header.component";
import {FooterComponent} from "src/app/components/layout/footer/footer.component";
import {NgxSpinnerModule} from "ngx-spinner";
import {ReactiveFormsModule} from "@angular/forms";
import {NgbToastModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    HeaderComponent,
    FooterComponent,

    ToastContainerComponent,
    HomeComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxSpinnerModule,
    ReactiveFormsModule,
    NgbToastModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
