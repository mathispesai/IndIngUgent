% Corporate report-template for KU Leuven en Kulak
% Stijn Rebry - december 2012
% Tips en hints voor een beter resultaat altijd welkom
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{kulakreport2013}[2013/08/29 Report class for KU Leuven]
\DeclareOption{kulak}{
   \providecommand{\Logo}{kulakLogo}
   }
\DeclareOption{kul}{
   \renewcommand{\Logo}{kuleuvenLogo}
   }
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{report}}
\ExecuteOptions{kulak}
\ProcessOptions\relax
\LoadClass{report}
\RequirePackage{hyperref}
\RequirePackage{titling}
\RequirePackage{multicol}
\RequirePackage{graphicx,xcolor}
	\definecolor{kulgroen}{rgb}{0.1137,0.5529,0.6902}
	\definecolor{grijs}{rgb}{0.25,0.25,0.25}
\RequirePackage{tikz}
	\usetikzlibrary{arrows} 
	\usetikzlibrary{decorations.pathmorphing}
	\usetikzlibrary{backgrounds}
	\usetikzlibrary{fit}
	\usetikzlibrary{shows}
	\usetikzlibrary{positioning}
\makeatletter
\newcommand\faculty[1]{\renewcommand\@faculty{#1}}
\newcommand\@faculty{}
\newcommand{\thefaculty}{\@faculty{}}
\newcommand\group[1]{\renewcommand\@group{#1}}
\newcommand\@group{}
\newcommand{\thegroup}{\@group{}}
\newcommand\subtitle[1]{\renewcommand\@subtitle{#1}}
\newcommand\@subtitle{}
\newcommand{\thesubtitle}{\@subtitle{}}
\newcommand\institute[1]{\renewcommand\@institute{#1}}
\newcommand\@institute{}
\newcommand{\theinstitute}{\@institute{}}
\newcommand\address[1]{\renewcommand\@address{#1}}
\newcommand\@address{}
\newcommand{\theaddress}{\@address{}}
\newcommand\emailaddress[1]{\renewcommand\@emailaddress{#1}}
\newcommand\@emailaddress{}
\newcommand{\theemailaddress}{\@emailaddress{}}
\makeatother
\RequirePackage{fancyhdr}
\pagestyle{fancy}
\renewcommand{\headrulewidth}{0pt}
\renewcommand{\footrulewidth}{0pt}
\fancyhead{}
\fancyfoot[CE,CO]{}
\fancyfoot[RO]{\footnotesize{\rightmark~$|$~\thepage}}
\fancyfoot[LE]{\footnotesize{\thepage~$|$~\leftmark}}
\renewcommand{\titlepage}{
\begin{tikzpicture}[remember picture,overlay]
     \node at (current page.south west)
     {
     \begin{tikzpicture}[remember picture, overlay,inner sep=0mm]
         \fill[kulgroen] (1.5,27.1) rectangle (19.5,25.3);
         \fill[kulgroen] (1.5, 2.5) rectangle (19.5, 2.7);
         \node[anchor=south west] at ( 2.0,26.0) {\includegraphics[height=1.8cm]{\Logo}};
         \node[anchor=east]       at (19.2,26.2) {\textcolor{white}{\textbf{\MakeUppercase{\thefaculty}}}};
         \node[anchor=north east] at (19.2,24.6) {\thegroup};
         \node[anchor=south west] at ( 2.6,18.5){\textcolor{black}{\Huge\bf\thetitle}};
         \node[anchor=north west] at ( 2.6,17.6){\LARGE\bf\thesubtitle};
         \node (datum) [anchor=south east] at (19.2, 3.5){\textcolor{grijs}{\large \thedate}};
         \node[anchor=south east, at=(    datum.north east),yshift=1.5cm] (instituut) {\textcolor{grijs}{\large \theinstitute}};
         \node[anchor=south east, at=(instituut.north east),yshift=0.5cm] {\textcolor{grijs}{\Large\bf\theauthor}};
      \end{tikzpicture}
      %\clearpage
      };
\end{tikzpicture}
\thispagestyle{empty}
\clearpage
%}
%\newcommand{\backcover}{
\AtEndDocument{
\clearpage%
  \ifodd\value{page}\mbox{}\thispagestyle{empty}\clearpage\fi\clearpage
\thispagestyle{empty}
\begin{tikzpicture}[remember picture,overlay]
     \node at (current page.south west)
     {
     \begin{tikzpicture}[remember picture, overlay,inner sep=0mm]
         \fill[kulgroen] ( 1.5, 2.5) rectangle (19.5,4.3);
         \fill[kulgroen] (13.5,27.6) rectangle (18.8,27.8);
         \fill[kulgroen] (18.6,27.8) rectangle (18.8,26.0);
         \node[anchor=south]      at (17.8,26.0) {\includegraphics[height=1.5cm]{kuleuvenSedes}};
         \node[anchor=north east] at (17.0,27.5) {\scalebox{.75}{\begin{tabular}{r}\theaddress\end{tabular}}};
      \end{tikzpicture}
      };
\end{tikzpicture}}
}