import { Component, OnInit } from '@angular/core';
import {ToastService} from "src/app/services/toast.service";

@Component({
  selector: 'app-toast-container',
  templateUrl: './toast-container.component.html',
  styleUrls: ['./toast-container.component.scss']
})
export class ToastContainerComponent implements OnInit {

  show = false;
  autohide = true;

  constructor(public toastService: ToastService) { }

  ngOnInit(): void {
  }

}
