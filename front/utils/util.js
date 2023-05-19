export const commaFormat = (str) => {
  str = uncommaFormat(str); // 이미 쉼표가 있는 경우 제거
  return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, "$1,");
};

export const uncommaFormat = (str) => {
  str = String(str);
  return str.replace(/[^\d]+/g, "");
};
