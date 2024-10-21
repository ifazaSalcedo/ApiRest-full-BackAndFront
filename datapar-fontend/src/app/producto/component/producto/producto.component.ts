import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { Producto } from '../../../model/producto';
import { ProductoService } from '../../service/producto.service';
import { InputNumberModule } from 'primeng/inputnumber';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [FormsModule,
    CommonModule,
    InputTextModule, CardModule, ButtonModule,
    DialogModule, CardModule, ConfirmDialogModule, ToastModule, InputNumberModule],
  providers: [ConfirmationService, MessageService],
  templateUrl: './producto.component.html',
  styleUrl: './producto.component.css'
})
export class ProductoComponent implements OnInit {



  producto: Producto = {
    codigo: '',
    nombre: "",
    descripcion: "",
    precioVenta: 0,
    stockActual: 0,
  };

  isDelete: boolean = false;
  isNew: boolean = false;

  validationErrors: { [key: string]: string } = {};

  constructor(private service: ProductoService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {

  }

  ngOnInit(): void {
    this.producto = this.config.data.producto;
    this.isNew = this.config.data.isNew;
    this.isDelete = this.config.data.isDelete;
  }



  saveProducto(event: Event) : void{
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Desea grabar los datos del Producto?',
      header: 'ATENCIÓN',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-text",
      accept: () => {
        if (this.isNew) {
          this.service.save(this.producto).subscribe(
            (clienteSave: any) => {
              this.messageService.add({ severity: 'info', summary: 'Operación completada', detail: 'Se ha agregado un nuevo producto', life: 3000 });
              this.closeDialogo(clienteSave);
            },
            (error: any) => {
              this.validationErrors= error.error;
              Object.entries(this.validationErrors).forEach(([key, value]) => {
                this.messageService.add({ severity: 'error', summary: 'Atención',
                  detail: value, life: 3000 });
              })
            }
          )
        } else {
          this.service.update(this.producto).subscribe(
            (clienteSave: any) => {
              this.messageService.add({ severity: 'info', summary: 'Operación completada', detail: 'Se ha actualizado los datos del producto', life: 3000 });
              this.closeDialogo(clienteSave);
            },
            (error: any) => {
              const _errorMensaje = error.error;
              this.messageService.add({ severity: 'error', summary: 'Atención', detail: _errorMensaje, life: 3000 });
            }
          )
        }
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Operación cancelada', detail: 'You have rejected', life: 3000 });
      }
    });
  }

  deleteProducto(_producto: Producto, event: Event) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Desea eliminar el registro del producto?',
      header: 'ATENCIÓN',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass:"p-button-danger p-button-text",
      rejectButtonStyleClass:"p-button-text p-button-text",
      acceptIcon:"none",
      rejectIcon:"none",

      accept: () => {
          this.service.delete(_producto.codigo).subscribe(
            ()=> {
              this.messageService.add({ severity: 'info', summary: 'Operación completada', detail: 'Se ha eliminado el registro del producto', life: 3000 });
              this.closeDialogo(_producto) ;
            },
            (error: any)=> {
              const _errorMensaje = error.error;
              this.messageService.add({ severity: 'error', summary: 'Atención', detail: _errorMensaje, life: 3000 });
            }
          );
      },
      reject: () => {

      }
  });
  }

  closeDialogo(_data: any) {
    setTimeout(() => {
      this.ref.close(_data);
    }, 1500)
  }

  closeDialogoImediate() {
    this.ref.close(null);
  }

}
