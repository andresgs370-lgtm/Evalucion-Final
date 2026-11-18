import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Pokemoncomponent } from './pokemoncomponent';

describe('Pokemoncomponent', () => {
  let component: Pokemoncomponent;
  let fixture: ComponentFixture<Pokemoncomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Pokemoncomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Pokemoncomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
