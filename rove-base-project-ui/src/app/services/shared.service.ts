import {Injectable} from '@angular/core';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private apiPath?: string;
  private uiPath?: string;

  constructor() {
  }

  getApiPath(platformLocation: any): string {
    if (!this.apiPath) {
      if (platformLocation.location.host.includes('localhost:4200') || platformLocation.location.pathname === '/') {
        // e.g. localhost:4200
        this.apiPath = environment.serverForNgServe + environment.apiName;
      } else {
        this.apiPath = platformLocation.location.origin + environment.apiName;
      }
    }
    return this.apiPath;
  }

  getUiPath(platformLocation: any): string {
    if (!this.uiPath) {
      if (platformLocation.location.host.includes('localhost:4200') || platformLocation.location.pathname === '/') {
        // e.g. localhost:4200
        this.uiPath = 'http://localhost:4200';
      } else {
        this.uiPath = platformLocation.location.origin + environment.apiName;
      }
    }
    return this.uiPath;
  }
}
