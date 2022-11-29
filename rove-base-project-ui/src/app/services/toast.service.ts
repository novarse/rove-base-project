import {Injectable, TemplateRef} from '@angular/core';

export interface ToastInfo {
  header: string;
  body: string;
  options: string;
  delay?: number;
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor() { }

  toasts: any[] = [];

  show(header: string, body: string, options: any = {}) {
    this.toasts.push({ header, body, options });
  }

  showOkMsg(body: string) {
    this.toasts.push({ header: '', body, options: {classname: 'bg-success text-light', delay: 3000} });
  }

  showWarning(body: string) {
    this.toasts.push({ header: '', body, options: {classname: 'bg-warning text-light', delay: 3000} });
  }

  showError(body: string) {
    this.toasts.push({ header: '', body, options: {classname: 'bg-danger text-light', delay: 4000} });
  }

  remove(toast: ToastInfo) {
    this.toasts = this.toasts.filter(t => t != toast);
  }
}
