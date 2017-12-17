import { BaseEntity, User } from './../../shared';

export const enum EstadoMensaje {
    'CREADO',
    ' ENVIADO',
    ' ACEPTADO',
    ' RECHAZADO'
}

export const enum TipoMensaje {
    'COBRO',
    ' PAGO',
    ' DEBITO',
    ' CREDITO'
}

export class Mensaje implements BaseEntity {
    constructor(
        public id?: number,
        public estado?: EstadoMensaje,
        public descripcion?: string,
        public monto?: number,
        public comentario?: string,
        public motivo?: string,
        public tipo?: TipoMensaje,
        public cuentaEmisor?: BaseEntity,
        public cuentaReceptor?: BaseEntity,
        public emisor?: User,
        public receptor?: User,
    ) {
    }
}
