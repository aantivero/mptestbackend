import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Cuenta } from './cuenta.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CuentaService {

    private resourceUrl = SERVER_API_URL + 'api/cuentas';

    constructor(private http: Http) { }

    create(cuenta: Cuenta): Observable<Cuenta> {
        const copy = this.convert(cuenta);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(cuenta: Cuenta): Observable<Cuenta> {
        const copy = this.convert(cuenta);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Cuenta> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Cuenta.
     */
    private convertItemFromServer(json: any): Cuenta {
        const entity: Cuenta = Object.assign(new Cuenta(), json);
        return entity;
    }

    /**
     * Convert a Cuenta to a JSON which can be sent to the server.
     */
    private convert(cuenta: Cuenta): Cuenta {
        const copy: Cuenta = Object.assign({}, cuenta);
        return copy;
    }
}
