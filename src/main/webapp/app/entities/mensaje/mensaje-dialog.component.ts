import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mensaje } from './mensaje.model';
import { MensajePopupService } from './mensaje-popup.service';
import { MensajeService } from './mensaje.service';
import { Cuenta, CuentaService } from '../cuenta';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mensaje-dialog',
    templateUrl: './mensaje-dialog.component.html'
})
export class MensajeDialogComponent implements OnInit {

    mensaje: Mensaje;
    isSaving: boolean;

    cuentas: Cuenta[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mensajeService: MensajeService,
        private cuentaService: CuentaService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cuentaService.query()
            .subscribe((res: ResponseWrapper) => { this.cuentas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mensaje.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mensajeService.update(this.mensaje));
        } else {
            this.subscribeToSaveResponse(
                this.mensajeService.create(this.mensaje));
        }
    }

    private subscribeToSaveResponse(result: Observable<Mensaje>) {
        result.subscribe((res: Mensaje) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Mensaje) {
        this.eventManager.broadcast({ name: 'mensajeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCuentaById(index: number, item: Cuenta) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mensaje-popup',
    template: ''
})
export class MensajePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mensajePopupService: MensajePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mensajePopupService
                    .open(MensajeDialogComponent as Component, params['id']);
            } else {
                this.mensajePopupService
                    .open(MensajeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
