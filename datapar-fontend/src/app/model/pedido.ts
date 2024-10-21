import { Cliente } from "../cliente/model/cliente";
import { PedidoDetalle } from "./pedidodet";


export class Pedido{
  codigo!: number;
  numeroControlPedido !: number;
  fechaHora!: Date;
  cliente: Cliente= new Cliente();
  estadoPedido!: string;
  detallePedidosDto: PedidoDetalle[] = [];

  convertEntityDto(){
    return {
      codigo: this.codigo,
      fechaHora: this.fechaHora,
      clienteId: this.cliente.codigo,
      estadoPedido: this.estadoPedido,
      detallePedidosDto: this.detallePedidosDto.map(_item=>({
        codigoPedido: this.codigo,
        codigoProducto: _item.producto.codigo,
        item: _item.item,
        cantidad: _item.cantidad,
        precioUnitario: _item.precioUnitario
      })),
    }
  }

}
