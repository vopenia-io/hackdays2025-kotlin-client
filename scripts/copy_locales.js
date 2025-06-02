const locales = [
  'fr',
  'en',
  'nl'
];

const files = [
  'home'
];

const fs = require('fs');
const path = require('path');

const meetOriginalDir = process.argv[2]
const folder = process.cwd();

console.log(folder);

const composeResources = `${folder}/shared/src/commonMain/composeResources`;

locales.forEach(locale => {
  // to manage case where compose will use values for en but values-lng for 'lng'
  const actualLocale = locale === 'en' ? 'values' : `values-${locale}`;

  const outputValuesDir = `${composeResources}/${actualLocale}`;
  if (!fs.existsSync(outputValuesDir)) {
    fs.mkdirSync(outputValuesDir);
  }
  const outputValues = `${outputValuesDir}/meet.xml`;

  if (fs.existsSync(outputValues)) {
    fs.rmSync(outputValues);
  }

  const objects = files.map(file => {
    const localFile = `${meetOriginalDir}/src/frontend/src/locales/${locale}/${file}.json`

    if (!fs.existsSync(localFile)) {
      return;
    }

    const content = fs.readFileSync(localFile, 'utf8');
    const data = content.toString();

    const json = JSON.parse(data);

    function valuesFromKey(prefix, holder) {
      if (typeof holder == "string") return { key: prefix, value: holder};

      return Object.keys(holder).map(sub_key => {
        const key = `${prefix}_${sub_key}`;
        return valuesFromKey(key, holder[sub_key]);
      }).flat();
    }

    return Object.keys(json).map(obj => valuesFromKey(`${file}_${obj}`, json[obj])).flat();
  }).flat();

  // console.log(objects);

  const firstOutput = objects.map(({key, value}) => `    <string name="${key}">${value}</string>` ).join('\n');
  const output = '<?xml version="1.0" encoding="utf-8"?>\n' +
  '<resources>\n' +
  `${firstOutput}\n` +
  '</resources>'

  fs.writeFileSync(outputValues, output);
});