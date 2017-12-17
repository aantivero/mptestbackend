/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DebinappTestModule } from '../../../test.module';
import { MensajeDialogComponent } from '../../../../../../main/webapp/app/entities/mensaje/mensaje-dialog.component';
import { MensajeService } from '../../../../../../main/webapp/app/entities/mensaje/mensaje.service';
import { Mensaje } from '../../../../../../main/webapp/app/entities/mensaje/mensaje.model';
import { CuentaService } from '../../../../../../main/webapp/app/entities/cuenta';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('Mensaje Management Dialog Component', () => {
        let comp: MensajeDialogComponent;
        let fixture: ComponentFixture<MensajeDialogComponent>;
        let service: MensajeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DebinappTestModule],
                declarations: [MensajeDialogComponent],
                providers: [
                    CuentaService,
                    UserService,
                    MensajeService
                ]
            })
            .overrideTemplate(MensajeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MensajeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MensajeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Mensaje(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.mensaje = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mensajeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Mensaje();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.mensaje = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mensajeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
