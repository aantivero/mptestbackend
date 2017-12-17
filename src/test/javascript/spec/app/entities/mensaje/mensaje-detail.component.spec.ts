/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { DebinappTestModule } from '../../../test.module';
import { MensajeDetailComponent } from '../../../../../../main/webapp/app/entities/mensaje/mensaje-detail.component';
import { MensajeService } from '../../../../../../main/webapp/app/entities/mensaje/mensaje.service';
import { Mensaje } from '../../../../../../main/webapp/app/entities/mensaje/mensaje.model';

describe('Component Tests', () => {

    describe('Mensaje Management Detail Component', () => {
        let comp: MensajeDetailComponent;
        let fixture: ComponentFixture<MensajeDetailComponent>;
        let service: MensajeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DebinappTestModule],
                declarations: [MensajeDetailComponent],
                providers: [
                    MensajeService
                ]
            })
            .overrideTemplate(MensajeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MensajeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MensajeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Mensaje(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mensaje).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
