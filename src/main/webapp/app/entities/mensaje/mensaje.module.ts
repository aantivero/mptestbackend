import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DebinappSharedModule } from '../../shared';
import { DebinappAdminModule } from '../../admin/admin.module';
import {
    MensajeService,
    MensajePopupService,
    MensajeComponent,
    MensajeDetailComponent,
    MensajeDialogComponent,
    MensajePopupComponent,
    MensajeDeletePopupComponent,
    MensajeDeleteDialogComponent,
    mensajeRoute,
    mensajePopupRoute,
} from './';

const ENTITY_STATES = [
    ...mensajeRoute,
    ...mensajePopupRoute,
];

@NgModule({
    imports: [
        DebinappSharedModule,
        DebinappAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MensajeComponent,
        MensajeDetailComponent,
        MensajeDialogComponent,
        MensajeDeleteDialogComponent,
        MensajePopupComponent,
        MensajeDeletePopupComponent,
    ],
    entryComponents: [
        MensajeComponent,
        MensajeDialogComponent,
        MensajePopupComponent,
        MensajeDeleteDialogComponent,
        MensajeDeletePopupComponent,
    ],
    providers: [
        MensajeService,
        MensajePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DebinappMensajeModule {}
