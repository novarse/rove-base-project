import { Injectable } from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {ToastService} from "src/app/services/toast.service";

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(public toastService: ToastService) { }


  public handleError<T>(operation = 'operation', result?: T) {
    return (error: HttpErrorResponse): Observable<T> => {
      let errorHeader = error.statusText ? error.statusText : 'Error occurred';

      if (error.error && Array.isArray(error.error)) {
        error.error.forEach((err: any) => {
          let msg = err.message;
          // let msg = (err.field ? err.field + ' ' : '') + err.message;

          if (msg.includes('Invalid courseId') && (err.field === 'courseId' || err.field === 'courseNr')) {
              msg = 'Enter a valid course ' + (err.field === 'courseId' ? 'id' : 'number');
          }

          this.toastService.show(errorHeader, msg, {classname: 'bg-danger text-light', delay: 5000});
        });
      } else {
        this.toastService.show(errorHeader, 'Please contact support', {classname: 'bg-danger text-light', delay: 5000});
      }

      return of(result as T);
    };
  }

}
