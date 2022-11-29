import {environment} from "src/environments/environment";

export class Utils {

    constructor() {
    }

    static getOrigin(platformLocation: any): string {
        if (platformLocation.location.pathname === '/') {
            // e.g. localhost:4200
            return environment.serverForNgServe;
        } else {
            return platformLocation.location.origin;
        }
    }

    static getQualTooltip(sector: string) {
        return 'HE' === sector ? 'Bachelor degrees and associate degrees' :
            ('VET' === sector ? 'Certificates, Diplomas and Advanced Diplomas' :
                    ('HE-Grad' === sector ? 'Higher Education and Postgraduate sectors' :
                            ('GET' === sector ? 'Master degree' :
                                    ('Grad' === sector ? 'Graduate Diploma, Master Degree and Doctorate' :
                                            ('HE-VET' === sector ? 'Bachelor degrees and associate degrees, Certificates, Diplomas and Advanced Diplomas' : '')
                                    )
                            )
                    )
            );
    }

    static getFeeTypetip(sector: string) {
        return 'CSP' === sector ? 'Commonwealth supported places' :
            ('DFP' === sector ? 'Domestic fee places' :
                    ('IFP' === sector ? 'International fee places' :
                            ('VFP' === sector ? 'Fee type determined by provider' : '')
                    )
            );
    }

    // EmailStr maybe an email or sometimes it is a link to a web page. Extract appropriate string.
    static getEmailOrWebLink(emailStr: string) {
        if (emailStr) {
        } else {
            return "";
        }

        let email = emailStr;
        let validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
        let validEmail = email.toLowerCase().match(validRegex);
        if (validEmail !== null) {
            return '<a href="mailto:' + email + '">' + email + '</a>';
        }
        let urlRegex = /(https?:\/\/[^ ]*)/;
        try {
            // @ts-ignore
            let url = email.match(urlRegex)[1];
            if (url !== null) {
                let pos = email?.indexOf(url);
                let part1 = email?.substring(0, pos);
                let part2 = "";
                pos += url.length;
                if (email?.length > pos) {
                    part2 = email?.substring(pos + 1);
                }
                return part1 + '<a href = "' + url + '" target="_blank">' + url + '</a>' + part2;
            }
        } catch (e: any) {
            return '<a href="https://' + email + '" target="_blank">' + email + '</a>'
        }
        return email;
    }


    static sleep(milliseconds: number) {
        let resolve: (value: (PromiseLike<unknown> | unknown)) => void;
        let promise = new Promise((_resolve) => {
            resolve = _resolve;
        });
        // @ts-ignore
        setTimeout(() => resolve(), milliseconds);
        return promise;
    }

    static isNumber(str: string): boolean {
        if (typeof str !== 'string') {
            return false;
        }

        if (str.trim() === '') {
            return false;
        }

        return !Number.isNaN(Number(str));
    }
}
