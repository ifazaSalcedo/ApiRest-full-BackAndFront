import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { Producto } from '../../../../../model/producto';
import { ProductoService } from '../../../../../producto/service/producto.service';
import { DynamicDialogRef, DynamicDialogConfig, DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { MessageService, ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-productobusq',
  standalone: true,
  imports: [FormsModule, CommonModule,InputIconModule,
    IconFieldModule,InputTextModule, TableModule,ButtonModule,DynamicDialogModule],
    providers: [DialogService, MessageService, ConfirmationService],
  templateUrl: './productobusq.component.html',
  styleUrl: './productobusq.component.css'
})
export class ProductobusqComponent {
  productos !: Producto[];
  productoSeleccionado !: Producto;
  textoFiltro !: string;
  page : number=0;
  size : number = 20;

  constructor(private service : ProductoService,
    public refBusqueda: DynamicDialogRef
  )  {

  }

  ngOnInit(): void {

  }

  filtrarProducto() : void {
    if(this.textoFiltro.trim() !== ""){
      this.service.findAllFilterProductoNombre(this.textoFiltro, this.page, this.size)
      .subscribe({
        next: (response) => {
          this.productos= response.content;
        }
      });
    }
  }

  selectProducto(_prodSel : Producto) : void {
    if(_prodSel){
      this.productoSeleccionado= _prodSel;
      this.refBusqueda.close(this.productoSeleccionado);
    }
  }

}
