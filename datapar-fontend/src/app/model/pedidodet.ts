import { Pedido } from "./pedido";
import { Producto } from "./producto";

export class PedidoDetalle{
  pedido!: Pedido;
  producto: Producto= new Producto();
  item !: number
  cantidad: number=0;
  precioUnitario: number=0;
  subtotal : number=0;
}
