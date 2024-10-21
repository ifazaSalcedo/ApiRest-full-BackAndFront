import { Injectable } from '@angular/core';
import { Ciudad } from '../../model/ciudad';
import { Page } from '../../model/page';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CiudadService {

  url = "http://localhost:8080/app/web/rest/ciudades";

  constructor(private http : HttpClient) { }

  findAll(page : number, pageSize : number) : Observable<Page<Ciudad>> {
    return this.http.get<Page<Ciudad>>(`${this.url}?page=${page}&size=${pageSize}`)
    //return this.http.get<Page<Ciudad>>(`${this.url}/product-list?page=${page}&size=${pageSize}`)
  }

}
