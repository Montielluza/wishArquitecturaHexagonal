import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'currencyFormat'
})
export class CurrencyFormatPipe implements PipeTransform {

  transform(value: number): string {
    if (value == null) return '';
    return '$' + value.toLocaleString('es-CO');
  }

}