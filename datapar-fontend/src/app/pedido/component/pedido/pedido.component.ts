import { asNativeElements, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FieldsetModule } from 'primeng/fieldset';
import { PanelModule } from 'primeng/panel';
import { Pedido } from '../../../model/pedido';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { SidebarModule } from 'primeng/sidebar';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { Producto } from '../../../model/producto';
import { PedidoDetalle } from '../../../model/pedidodet';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogService, DynamicDialogModule, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { CargarProductoComponent } from '../../../varios/component/cargar-producto/cargar-producto.component';
import { Footer } from '../../../varios/foosterButton';
import { ClienteService } from '../../../cliente/service/cliente.service';
import { Cliente } from '../../../cliente/model/cliente';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { PedidoService } from '../../service/pedido.service';
import { EstadoPedido } from '../../../model/estadoped';
import { ChipModule } from 'primeng/chip';
import { errorContext } from 'rxjs/internal/util/errorContext';
import { ClientebusqComponent } from '../../../varios/dialogbusq/cliente/componente/clientebusq/clientebusq.component';
import { AutoFocusModule } from 'primeng/autofocus';
import { first } from 'rxjs';

@Component({
  selector: 'app-pedido',
  standalone: true,
  imports: [FormsModule, CommonModule,AutoFocusModule,
    FieldsetModule, InputTextModule,
    TableModule, SidebarModule, ButtonModule,
    CardModule, InputNumberModule, InputTextModule,
    DynamicDialogModule, ToastModule, ConfirmPopupModule,
    ConfirmDialogModule, PanelModule,ChipModule],
  providers: [DialogService, MessageService, ConfirmationService],
  templateUrl: './pedido.component.html',
  styleUrl: './pedido.component.css'
})
export class PedidoComponent implements OnInit{

  @ViewChild('txtRuc') txtRuc!: ElementRef;
  @ViewChild('btnAgregarItem') btnAgregarItem!: ElementRef;

  ref: DynamicDialogRef | undefined;
  pedido: Pedido = new Pedido();
  dialogoVisible: boolean = false;
  detallePedido: PedidoDetalle = new PedidoDetalle();

  counterItem: number = 0;
  tatalityPedido: number = 0;
  pedidoNumero : string="";
  pedidoFecha : string = "";

  constructor(private clienteService: ClienteService,
    private pedidoService: PedidoService,
    public dialogService: DialogService,
    public messageService: MessageService,
    private confirmationService: ConfirmationService) {

  }
  ngOnInit(): void {
    this.obtenerFecha();
    this.obtenerNumeroPedido();
  }
  obtenerNumeroPedido() {
    this.pedidoService.controlNumeroPedido().subscribe({
      next: (_orden: any) => {
        this.pedidoNumero = _orden.numeroPedido;
      },
      error: (_error: any) => {
        this.messageService.add({ severity: 'error', summary: 'Atención',
          detail: "No se puede obtener el numero del pedido", life: 3000 });
      }
    });
  }
  obtenerFecha() {
    this.pedidoService.obtenerFechaServidor().subscribe({
      next: (_fecha: any) => {
        this.pedidoFecha= _fecha.fechaServer
      },
      error: (error: any) => {
        this.messageService.add({ severity: 'error', summary: 'Atención',
          detail: "No se pudo recuperar la fecha del servidor", life: 3000 });
          console.error(error);
      }
    });
  }
  stateDetalleDialog(_estado: boolean) {
    this.openDialogo(this.detallePedido);
  }
  openDialogo(_detallePedido: PedidoDetalle) {
    this.ref = this.dialogService.open(CargarProductoComponent, {
      data: { detalle: new PedidoDetalle() },
      header: 'Cargar detalle de producto',
      footer: "",
      width: '50vw',
      contentStyle: { overflow: 'auto' },
      breakpoints: {
        '960px': '75vw',
        '640px': '90vw'
      },
      styleClass: 'no-animation',
      templates: {
        footer: Footer
      }
    })

    this.ref.onClose.subscribe((data: PedidoDetalle) => {
      let summary_and_detail;
      if (data) {
        this.addDetalle(data)
      } else {

      }

    });

  }

  openDialogBusquedaCliente(){
    this.ref = this.dialogService.open(ClientebusqComponent, {
      data: { cliente: new Cliente() },
      header: 'Buscar Cliente',
      footer: "",
      width: '50vw',
      contentStyle: { overflow: 'auto' },
      breakpoints: {
        '960px': '75vw',
        '640px': '90vw'
      }
    })

    this.ref.onClose.subscribe((_clienteSeleccionado: Cliente) => {
    if (_clienteSeleccionado) {
        this.pedido.cliente = _clienteSeleccionado;
      } else {

      }
    });


  }

  addDetalle(_item: PedidoDetalle) {
    this.pedido.detallePedidosDto.push(_item);
    this.orderItem();
  }

  orderItem() {
    let nroOrden: number = 0;

    this.counterItem = 0;
    this.tatalityPedido = 0;

    this.pedido.detallePedidosDto.forEach((item: PedidoDetalle) => {
      item.item = ++nroOrden;
      this.tatalityPedido = this.tatalityPedido + item.subtotal;
      this.counterItem++;
    });


  }

  deleteItem(_detalle: PedidoDetalle, event: Event) {
    this.confirmationService.confirm({
      key: "popup",
      target: event.target as EventTarget,
      message: 'Desea eliminar el item?',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass: 'p-button-danger p-button-sm',
      accept: () => {
        this.pedido.detallePedidosDto = this.pedido.detallePedidosDto.filter((_item => _detalle.item !== _item.item));
        this.messageService.add({ severity: 'info', summary: 'ATENCIÓN', detail: 'Item eliminado!', life: 3000 });
      },
      reject: () => {

      }
    });

  }

  buscarCliente(): void {
    if (this.pedido.cliente.documentoNro) {
      this.clienteService.findByRucOrDocument(this.pedido.cliente.documentoNro)
        .subscribe(
          (response: Cliente) => {
            this.pedido.cliente = response;
            this.btnAgregarItem.nativeElement.focus();
          },
          (error: any) => {
            this.txtRuc.nativeElement.focus();
            this.messageService.add({ severity: 'error', summary: 'Atención', detail: error.error, life: 3000 });
          }
        );
    }
  }
  reset() {
    this.ref = undefined;
    this.pedido = new Pedido();
    this.dialogoVisible = false;
    this.detallePedido = new PedidoDetalle();

    this.counterItem = 0;
    this.tatalityPedido = 0;

    this.obtenerNumeroPedido();
    this.obtenerFecha();

    this.txtRuc.nativeElement.focus();

  }
  savePedido(event: Event) {
    this.confirmationService.confirm({
      key: "dialog",
      target: event.target as EventTarget,
      message: 'Desea grabar los datos del cliente?',
      header: 'ATENCIÓN',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-text",
      accept: () => {
        if (!this.pedido.estadoPedido) {
          this.pedido.estadoPedido = EstadoPedido.PENDIENTE;
        }
        this.pedidoService.save(this.pedido).subscribe({
          next: (data: any) => {
            this.messageService.add({ severity: 'info', summary: 'Operación completada', detail: 'Se ha agregado un nuevo pedido', life: 3000 });
            this.reset();
          },
          error : (err: any) =>{
            this.messageService.add({ severity: 'error', summary: 'Atención', detail: err.error, life: 3000 });
          },
        });
      }
    });


  }

}
