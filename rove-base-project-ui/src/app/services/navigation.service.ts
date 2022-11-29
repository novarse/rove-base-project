import { Injectable } from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {Location} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  history: string[] = [];

  constructor( private router: Router,
               private location: Location) {

    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.history.push(event.urlAfterRedirects)
      }
    });
  }

  backToSearch() {
    this.history.pop()
    if (this.history.length > 0 && this.history[this.history.length - 1].includes('search')) {
        this.location.back()
    } else {
      this.router.navigate(['search']);
    }
  }
}
