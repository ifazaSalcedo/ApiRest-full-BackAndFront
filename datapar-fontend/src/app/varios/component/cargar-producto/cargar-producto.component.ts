import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ProductoService } from '../../../producto/service/producto.service';
import { PedidoDetalle } from '../../../model/pedidodet';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { FieldsetModule } from 'primeng/fieldset';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { SidebarModule } from 'primeng/sidebar';
import { TableModule } from 'primeng/table';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService, DynamicDialogConfig, DynamicDialogModule, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { Producto } from '../../../model/producto';
import { ProductobusqComponent } from '../../dialogbusq/producto/componente/productobusq/productobusq.component';

@Component({
  selector: 'app-cargar-producto',
  standalone: true,
  imports: [FormsModule,
    CommonModule,
    FieldsetModule, InputTextModule,
    TableModule, SidebarModule, ButtonModule,
    CardModule,InputNumberModule,InputTextModule,
    DynamicDialogModule,
    ToastModule,ConfirmDialogModule,ToastModule],
  providers: [DialogService, MessageService,ConfirmationService],
  templateUrl: './cargar-producto.component.html',
  styleUrl: './cargar-producto.component.css'
})
export class CargarProductoComponent implements OnInit{
  @ViewChild('txtCodigoArt', { static: false })  txtCodigoArt!: ElementRef;
  @ViewChild('txtCantidad', { static: false })  txtCantidad!: ElementRef;

  detallePedido : PedidoDetalle= new PedidoDetalle();

  isComplete : boolean = false;

  refBusqueda: DynamicDialogRef | undefined;

  constructor(private productoService: ProductoService,
    public dialogService: DialogService,
    public dialogService2: DialogService,
    public messageService: MessageService,
    public conf: DynamicDialogConfig,
    public ref: DynamicDialogRef) {

  }

  ngOnInit(): void {
    this.detallePedido= this.conf.data.detalle;
  }

  buscarProducto() : void {
    if(this.detallePedido.producto.codigo){
      this.productoService.findById(this.detallePedido.producto.codigo).subscribe(
        (res: Producto)=>{
          this.detallePedido.producto = res;
          this.detallePedido.precioUnitario= res.precioVenta;
          this.detallePedido.cantidad=1;
          this.calcularSubTotal();
        },
        (error: any)=>{
          this.txtCodigoArt.nativeElement.focus();
          this.messageService.add({ severity: 'error', summary: 'Atención', detail: error.error, life: 3000 });
        }
      );
    }
  }

  calcularSubTotal() : void {
    if(this.detallePedido.cantidad !== 0 && this.detallePedido.producto){
      if(this.detallePedido.precioUnitario && this.detallePedido.cantidad){
        this.detallePedido.subtotal= this.detallePedido.cantidad * this.detallePedido.precioUnitario
        this.isComplete=true;
      }else{
        this.isComplete=false;
        this.detallePedido.subtotal=0;
      }
    }
  }

  cargarDetalle(_detalle : PedidoDetalle){
    this.ref.close(_detalle);
  }

  openDialogoBusquedaProducto(){

    this.refBusqueda = this.dialogService2.open(ProductobusqComponent, {
      data: {},
      header: 'Busqueda de productos',
      footer: "",
      width: '50vw',
      contentStyle: { overflow: 'auto' },
      breakpoints: {
        '960px': '75vw',
        '640px': '90vw'
      }
    })

    this.refBusqueda.onClose.subscribe((data :any) => {
      if(data){
        this.detallePedido.producto = data;
        this.detallePedido.precioUnitario= data.precioVenta;
      }
    });
  }

}
