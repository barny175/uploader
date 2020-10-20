import { TestBed } from '@angular/core/testing';

import { FileService } from './list-files.service';

describe('ListFilesService', () => {
  let service: FileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
