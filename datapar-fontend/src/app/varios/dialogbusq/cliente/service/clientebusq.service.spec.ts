import { TestBed } from '@angular/core/testing';

import { ClientebusqService } from './clientebusq.service';

describe('ClientebusqService', () => {
  let service: ClientebusqService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClientebusqService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
