import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PartyIdentificationService } from 'app/entities/party-identification/party-identification.service';
import { IPartyIdentification, PartyIdentification } from 'app/shared/model/party-identification.model';
import { IdentificationType } from 'app/shared/model/enumerations/identification-type.model';

describe('Service Tests', () => {
  describe('PartyIdentification Service', () => {
    let injector: TestBed;
    let service: PartyIdentificationService;
    let httpMock: HttpTestingController;
    let elemDefault: IPartyIdentification;
    let expectedResult: IPartyIdentification | IPartyIdentification[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PartyIdentificationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PartyIdentification(0, IdentificationType.KTP, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            validDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PartyIdentification', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            validDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validDate: currentDate,
          },
          returnedFromService
        );

        service.create(new PartyIdentification()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PartyIdentification', () => {
        const returnedFromService = Object.assign(
          {
            identificationType: 'BBBBBB',
            identNo: 'BBBBBB',
            validDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PartyIdentification', () => {
        const returnedFromService = Object.assign(
          {
            identificationType: 'BBBBBB',
            identNo: 'BBBBBB',
            validDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PartyIdentification', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
