import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {PlatformLocation} from "@angular/common";
import {SharedService} from "src/app/services/shared.service";

@Injectable()
export class HeaderInterceptor implements HttpInterceptor {

  constructor(private platformLocation: PlatformLocation,
              private shared: SharedService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const url = this.shared.getApiPath(this.platformLocation) + req.url;

    return next.handle(req.clone({
      url,
      headers: this.getHeaders(req.headers)
    }));
  }

  getHeaders(headers: HttpHeaders) {
    headers = headers.set('Content-Type', 'application/json');
    return headers;
  }

}
