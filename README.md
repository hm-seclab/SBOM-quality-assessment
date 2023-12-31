# SBOM generation quality assessment

This is the repository behind the website https://sbom.seclab.cs.hm.edu/

### Due to maintenance work on the university's power grid the SBOM quality assessment is offline over the weekend between 1. of september and 4. of september.

This project is part of a research project for a master's thesis at the University of Applied Sciences Munich in the [HM-Seclab](https://seclab.cs.hm.edu/). Feel free to reach out at mbiebel[at].hm.edu or hit me up on Mastodon at [@mariuxdeangelo@infosec.exchange](https://infosec.exchange/@mariuxdeangelo).

I'm currently work a lot together with the awesome people from the [OpenSSF SBOM Everywhere Working Group](https://github.com/ossf/sbom-everywhere). You can find me in there bi-weekly meetings. We are always happy for people to join.

## For people looking at the SBOM Website

Please be aware that all the data is autogenerated. Be careful drawing any conclusions from the data. It is very likely that there are biases that were introduced while generating.

- The generator might not be applicable to the programming language or build tools used in the subject project.
- There might be issues converting between SBOM formats.
- There might be issues with the environment (e.g. wrong JDK version).

Each SBOM comes with lots of documentation on the generation, like the command used, log output, or if there was a timeout.

Currently, there is no documentation on all the features on the website because it's still work in progress. I hope I find time for this soon.

## For developers coming over from an SBOM tool

Thank you for checking out my work. I hope my website could support you with your projects. Please feel free to send me an email or open an issue if you have questions or found some bugs.

I also would be very happy if you could review the commands used to generate an SBOM with your tooling and check the logs. They are all available on my website together with the SBOMs themselves. The logs are recorded with asciinema. You can replay them in the browser or download them and inspect them with the asciinema CLI.

## FAQ

**Have you encountered any issues or do you have any feedback on the generators?**

Yes, I would be happy to give you feedback and share my findings. I'm currently still working on fixing some bugs and compiling my findings, so I can report them properly. I also found issues that are out of scope for my paper, but I still plan to open issues or get in touch with the developers. I also thank everybody who already helped me out here.

**Have you thought about using tools at build time like all the CycloneDx plugins? The data quality will be much better.**

Yea, I know about them, but I consciously defined them as out of scope for this paper because it's very hard to apply them to a large sample of projects. Also, one can assume that they only convert between the package manager / build system and the SBOM format. So by definition, this is the best quality of data you might get. I would always recommend this approach if time and resources are available to integrate them properly. I hope to investigate these plugins in further work.

**Can you add project XYZ to your tool?**

Yes, of course. Just hit me up with your repository / container name, and I will add them. But please be aware that the SBOMs are autogenerated and should be used with care. Please <ins>don't</ins> add them to your projects release.

**Do you have a ranking of which generator is best?**

Nope, I'm currently very careful compiling a KPI that suggests such statements. From experience, I know each time someone introduces a KPI to measure success, people find a way to abuse the metric to boost their numbers. It would be very bad if developers start to add duplicates or false positives just to make their tool look good. I want this to be a contribution to the SBOM community, and I think putting up some KPIs based on data that might be biased is very destructive at this point in time.

If you are looking for a good tool, I can recommend using plugins for your build system / package manager if available and if you have the time to properly integrate them, because they might produce the best data. You can also go with a global generator like I use here because they are easier to integrate, but in this case, check what generators support your tooling and programming languages best.

## For contributors to this project

Hey, thanks for helping out. If you looked through my codebase, you might find some mess... sorry for that, but at some parts, I'm just trying to figure it out while I go along. Also, I used this project as an opportunity to learn SpringBoot and Vue.js.

The repository is based on Gradle and separated into 4 modules.

- **The Generator** is running inside a Docker container with all the tools needed to execute all the SBOM-Generators and write all the data to the Database. The Generator lives on another server than the Website and Database.
- **The Backend** is a SpringBoot REST service that hosts the Website and the REST service.
- **The Frontend** is not a java but a npm project where the whole Vue.js implementation of the website lives. The frontend gets built into the SpringBoot backend and is shipped there as a static resource.
- **The Database** implements all the Objects and manages all the read and write queries. The database module is used by the generator module and the backend module for the Website.