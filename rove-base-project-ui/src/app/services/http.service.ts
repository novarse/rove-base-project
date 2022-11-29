import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  // getSuggestions(period?: number, text?: string): Observable<string[]> {
  //   return this.http.get<string[]>(`/api/course/suggest/${period}/${text}`);
  // }

}
