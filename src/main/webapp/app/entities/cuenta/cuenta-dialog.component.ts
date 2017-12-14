import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Cuenta } from './cuenta.model';
import { CuentaPopupService } from './cuenta-popup.service';
import { CuentaService } from './cuenta.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-cuenta-dialog',
    templateUrl: './cuenta-dialog.component.html'
})
export class CuentaDialogComponent implements OnInit {

    cuenta: Cuenta;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cuentaService: CuentaService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.cuenta.user = new User();
        this.isSaving = true;
        if (this.cuenta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cuentaService.update(this.cuenta));
        } else {
            this.subscribeToSaveResponse(
                this.cuentaService.create(this.cuenta));
        }
    }

    private subscribeToSaveResponse(result: Observable<Cuenta>) {
        result.subscribe((res: Cuenta) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Cuenta) {
        this.eventManager.broadcast({ name: 'cuentaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cuenta-popup',
    template: ''
})
export class CuentaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cuentaPopupService: CuentaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cuentaPopupService
                    .open(CuentaDialogComponent as Component, params['id']);
            } else {
                this.cuentaPopupService
                    .open(CuentaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
