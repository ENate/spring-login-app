import { TestBed } from '@angular/core/testing';

import { MandrakeService } from './mandrake.service';

describe('MandrakeService', () => {
  let service: MandrakeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MandrakeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
