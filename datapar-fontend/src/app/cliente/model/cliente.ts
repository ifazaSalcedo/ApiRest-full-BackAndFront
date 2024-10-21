import { Ciudad } from "../../model/ciudad";

export class Cliente {
  codigo !: number;
  nombre !: string;
  documentoNro !: string;
  ciudad !: Ciudad;
  direccion !: string;
  telefono !: string;
  email !: string;
}
