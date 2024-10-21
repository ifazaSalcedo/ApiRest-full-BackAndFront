import { Component, OnInit } from '@angular/core';
import { Producto } from '../../../model/producto';
import { Page } from '../../../model/page';
import { DialogService, DynamicDialogModule, DynamicDialogRef } from 'primeng/dynamicdialog';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TableModule, TablePageEvent } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { ProductoService } from '../../service/producto.service';
import { MessageService } from 'primeng/api';
import { ProductoComponent } from '../producto/producto.component';
import { PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'app-producto-list',
  standalone: true,
  imports: [CommonModule, FormsModule,
    TableModule, ButtonModule, CardModule, DynamicDialogModule,
    ToastModule, ConfirmDialogModule,PaginatorModule],
  providers: [DialogService, MessageService],
  templateUrl: './producto-list.component.html',
  styleUrl: './producto-list.component.css'
})
export class ProductoListComponent implements OnInit {


  productos !: Producto[];
  pagen !: Page<Producto>;
  ref: DynamicDialogRef | undefined;
  totalItems: number = 0;
  page: number = 0;
  size: number = 10;
  totalPages: number = 0;

  constructor(private service: ProductoService,
    public dialogService: DialogService,
    public messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.findAllProductos();
  }

  findAllProductos() {
    this.service.findAll(this.page, this.size).subscribe(
      {
        next: (_pageResponse) => {
          this.pagen = _pageResponse;
          this.productos= this.pagen.content;
          this.totalItems = _pageResponse.page.totalElements;
          this.totalPages = _pageResponse.page.totalPages;
          this.size = _pageResponse.page.size;
          this.page = _pageResponse.page.number;
        }
      }
    );
  }

  cambiarPagina(event: any): void {
    this.page = event.page;
    this.findAllProductos();
  }

  addProducto() {
    this.openDialogo(new Producto(), true, false);
  }

  deleteProducto(_producto: Producto) {
    this.openDialogo(_producto, false, true);
  }
  editProducto(_producto: Producto) {
    this.openDialogo(_producto, false, false);
  }

  openDialogo(_producto: Producto, _new: boolean, _delete: boolean) {
    this.ref = this.dialogService.open(ProductoComponent, {
      data: {
        producto: { ..._producto },
        isNew: _new,
        isDelete: _delete
      },
      header: 'Producto/Articulo',
      footer: "",
      width: '50vw',
      contentStyle: { overflow: 'auto' },
      breakpoints: {
        '960px': '75vw',
        '640px': '90vw'
      }
      /*templates: {
        footer: Footer
      }*/
    });
    this.ref.onClose.subscribe((data: any) => {
      if (data) {
        //this.messageService.add({ severity: 'info', summary: 'Operación completada', detail: 'Se ha agregado un nuevo cliente', life: 3000 });
        this.findAllProductos();
      } else {

      }
    });
  }

}
